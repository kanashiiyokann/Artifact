function Cacher() {
    let dataStore = window.localStorage;
    this.post = function (options) {
        let defaults = {
            url: undefined,
            data: undefined,
            success: function () {
                console.error("not implements success!");
            },
            error: function () {
                console.warn("not implements error!");
            },
        };


    }

    /**
     * 缓存数据
     * @param key
     * @param request
     */
    this.storage = function (key, request) {

        let defaults = {
            url: undefined,
            data: undefined,
            type: "POST",
            success: function (data) {
                if (data !== null && data !== undefined && data !== "") {
                    dataStore.setItem(key, data);
                }
            }
        };

        defaults.$merge(request);
        if (dataStore.getItem(key) === undefined) {
            sendAjax(request);
        }

    };


    function sendAjax(option) {
        let defaults = {
            type: "POST",
            url: undefined,
            data: undefined,
            async: true,
            success: function () {
                console.error("not implements success!");
            }
        };

        defaults.$merge(option);

        let request;
        if (window.XMLHttpRequest) {
            //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
            request = new XMLHttpRequest();
        } else {
            // IE6, IE5 浏览器执行代码
            request = new ActiveXObject("Microsoft.XMLHTTP");
        }
        request.onreadystatechange = function () {
            if (request.readyState == 4 && request.status == 200) {
                defaults.success(request.response);
            }
        }
        request.open(defaults.type, defaults.url, defaults.async);
        request.send(defaults.data);

    }


    Object.prototype.$merge = function (object) {
        for (let key in object) {
            this[key] = object[key];
        }
        return this;
    }
}