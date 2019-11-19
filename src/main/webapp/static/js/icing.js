class Icing {
    constructor(data) {
        this.$data = data;
    }

    strip(path,def) {
        path = path || '';
        let pathArr = path.split(".");
        let obj = this.$data;
        while (typeof obj === "object" && pathArr.length > 0) {
            obj = obj[pathArr.shift()];
        }
        let ret= pathArr.length === 0 ? obj : undefined;

        if(def!==undefined && ret===undefined){
            ret=def;
        }
        return ret;
    }

}