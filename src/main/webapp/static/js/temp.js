class Test {

    constructor(fn) {
        this._fn = fn;
    }

    static of(fn) {
        return new Test(fn);
    }

    catch(fn) {
        this._reject = fn;
        return this;
    }

    then(fn) {
        this._accept = fn;
        return this;
    }

    next(fn) {
        let next = Test.of(fn);
        this._accept = function (data) {
            next._deliver = true;
            next.execute(data);
        };
        this._next = next;
        next._prev = this;

        return next;
    }

    execute(data) {
        let cursor = this;
        while (!cursor._deliver && cursor._prev) {
            cursor = cursor._prev;
        }
        delete cursor._deliver;
        cursor._fn.call(undefined, cursor._accept, cursor._reject, data);

    }
}