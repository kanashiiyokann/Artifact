class Test {

    constructor(fn) {
    this.fn=fn;
    console.log(fn);
    }

    static of(fn) {
        return new Test(fn);
    }

}


// let chain=
//     Test.of((accept, reject,data) => {}).catch()
//     .next((accept, reject,data) => {}).catch()
//         .next((accept, reject,data) => {}).catch().then();
//
// chain.start();
