export default {
    test: function (fn, arg) {
        if (typeof fn === 'function')
            fn.call(null, arg);
    }
};
