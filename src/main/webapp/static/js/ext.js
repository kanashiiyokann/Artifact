
/**
 * 取值包装类
 */
class Icing {
    constructor(obj) {
        this.obj = obj;
    };

    static of(obj) {
        return new Icing(obj);
    }

    strip(path, def) {
        path = path || '';
        let pathArr = path.split(".");
        let obj = this.obj;
        while (typeof obj === "object" && pathArr.length > 0) {
            obj = obj[pathArr.shift()];
        }
        let ret = pathArr.length === 0 ? obj : undefined;

        if (def !== undefined && ret === undefined) {
            ret = def;
        }
        return ret;
    }
}

/**
 * 模板填充类
 */
class Template {
    constructor(temp) {
        this.temp = temp || '';
        this.reg_ph = /{{.[^{}]+}}/g;
        this.reg_key = /[a-zA-Z]+/;
    }

    static of(temp) {
        return new Template(temp);
    }

    fill(obj) {
        if (typeof obj !== 'object') {
            throw new Error("argument error:object only!");
        }
        let htm = this.temp;
        let placeHolderList = htm.match(this.reg_ph);

        placeHolderList.forEach(ph => {
            let key = ph.match(this.reg_key);
            key = key.length > 0 ? key[0] : "";
            if (key !== "") {
                htm = htm.replace(ph, obj[key] || '');
            }
        });

        return htm;
    }

}

class Table{
    constructor(selector){
        this.$instance=$(selector);
        this.setting={default:{}};
        this.setting.default.value="";
        let $this=this;
        this.setting.default.render=function(data,object){
            return data||$this.setting.default.value;
        };
    }
    static of(selector){
        return new Table(selector);
    }
    map(mapper){
        this.mapper=mapper;
        return this;
    }
    load(data){
        let $this=this;
        if(!Array.isArray(this.mapper)){
            return ;
        }
        //添加表头
        if(!this.$instance.find("thead").length){
            this.$instance.prepend('<thead></thead>');
        }
        if(!this.$instance.find("th").length){
            this.$instance.find('thead').append($this.mapper.map(e=>'<th>'+e["title"]+'</th>').join(""));
        }
        //
        if(!this.$instance.find("tbody").length){
            this.$instance.append("<tbody></tbody>");
        }
        let $tbody=this.$instance.find("tbody");
        $tbody.html('');

        data.forEach(function(row,index,table){
            $this._digestData(row);
        });


    }

    _digestData(object){
        let $this=this;
        let $tbody=this.$instance.find("tbody");
            object=Icing.of(object);

        let arrColumn= this.mapper.find(e=>e["data"].indexOf(":")>-1);
        let pathArr=arrColumn["data"].split(":");
        let array= object.strip(pathArr[0],[{}]);
        let len=array.length;
        let first=Icing.of(array.shift());

        let htm='<tr role="row">';
        this.mapper.forEach(function(column, index, arr){
            let data;
            let name=column["data"];
            if(name.indexOf(':')>-1){
                data=first.strip(name.split(":")[1]);
                    htm+='<td>';
            }else{
                htm+='<td rowspan="'+len+'">';
                data=object.strip(name);
            }
            let render=column['render']||$this.setting.default.render;
            data=render(data,object.obj);
            htm+=(data+'</td>');
        });
        htm+='</tr>';
        $tbody.append(htm);
        let $tr=$tbody.find("tr:last");
        $tr.prop("data-row",object.obj);
        //补足
     let extra=  this.mapper.filter(e=>e["data"].indexOf(":")>-1);

        while(array.length>0){
            let row=Icing.of(array.shift());
            let str='<tr>';
            extra.forEach(e=> {
                let name=e["data"].split(":")[1];
                let render=e["render"]||$this.setting.default.render;
                str+=('<td>'+render(row.strip(name))+'</td>');
            });
            str+='</tr>';
            $tbody.append(str);
        }
    }

}


