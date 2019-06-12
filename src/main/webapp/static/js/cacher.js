/**
 * 页面缓存器，缓存固定url请求的数据
 */
function Cacher() {
    this.ajax = function (options) {
        let target = '';
        let param = options.data || null;
        if (param !== null) {
            for (let key in param) {
                if (param.hasOwnProperty(key)) {
                    target += ("&" + key + "=" + param[key]);
                }
            }
            target = '?' + target.substring(1);
        }
        target = options.url + target;
        let data = tryParseJson(sessionStorage.getItem(target));

        let resolve = options.success;
        if (typeof resolve !== "function") {
            console.error("success in option must be function!");
        }
        if (data !== null) {
            resolve(data);
        } else {
            options.success = function (res) {
                let ret = resolve(res);
                if (ret !== false) sessionStorage.setItem(target, tryParseJsonString(res));
            };
            sendHttpRequest(options);
        }
    };

    /**
     * 尝试转换成json对象
     * @param data
     * @returns {*}
     */
    function tryParseJson(data) {
        try {
            data = JSON.parse(data);
        } catch (e) {

        }
        return data;
    }

    /**
     * 尝试转换成json字符串
     * @param data
     * @returns {*}
     */
    function tryParseJsonString(data) {
        if (typeof data === "object") {
            data = JSON.stringify(data);
        }
        return data;
    }

    /**
     * 发送http请求
     * @param options ajax请求参数
     */
    function sendHttpRequest(options) {

        let defaults = {
            url: null,
            type: "POST",
            async: true,
            success: function (data) {
                console.log(data);
            },
            error: function (args) {
                console.error(args);
            }
        };
        defaults = extend(defaults, options);

        if (!XMLHttpRequest) {
            console.error("当前浏览器版本过低,不支持XMLHttpRequest!");
        }
        let xhr = new XMLHttpRequest();
        xhr.open(defaults.type, defaults.url, defaults.async);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {

                if (xhr.status === 200 || xhr.status === 304) {
                    let data = xhr.responseText;
                    try {
                        data = JSON.parse(data);
                    } catch (e) {
                    }
                    defaults.success.call(this, data);
                }

            } else {
                defaults.error(arguments);
            }
        };
        if (defaults.data !== null && defaults.data !== undefined) {
            xhr.send(defaults.data);
        } else {
            xhr.send();
        }
    }

    /**
     *  合并数据对象
     * @param obj
     * @param obj2
     * @returns {*}
     */
    function extend(obj, obj2) {
        if (typeof obj2 === 'object') {
            for (let key in obj2) {
                obj[key] = obj2[key];
            }
        }
        return obj;
    }
}

let cacher = new Cacher();
window.$cacher = cacher;

export {cacher} ;


