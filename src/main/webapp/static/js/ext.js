Object.prototype.$strip = function (path, def) {
    path = path || '';
    let pathArr = path.split(".");
    let obj = this;
    while (typeof obj === "object" && pathArr.length > 0) {
        obj = obj[pathArr.shift()];
    }
    let ret = pathArr.length === 0 ? obj : undefined;

    if (def !== undefined && ret === undefined) {
        ret = def;
    }
    return ret;
};


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
        this.reg_ph = /{{.+}}/g;
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




