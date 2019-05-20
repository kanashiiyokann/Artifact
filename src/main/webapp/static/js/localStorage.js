export default {
    "name": "localStorage",
    set(key, value) {
        window.localStorage.setItem(key, JSON.stringify(value));
    },
    get(key, def) {
        def = def || "";

        let ret = window.localStorage.getItem(key);
        try {
            ret = JSON.parse(ret);
        } catch (e) {
            ret = def;
            console.warn(e.toString());
        }

        return ret;
    }
};
