

Object.prototype.$strip=function(path,def){
    path = path || '';
    let pathArr = path.split(".");
    let obj = this;
    while (typeof obj === "object" && pathArr.length > 0) {
        obj = obj[pathArr.shift()];
    }
    let ret= pathArr.length === 0 ? obj : undefined;

    if(def!==undefined && ret===undefined){
        ret=def;
    }
    return ret;
};