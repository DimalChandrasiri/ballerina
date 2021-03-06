import ballerina/test;
import ballerina/io;

any[] outputs = [];
int counter = 0;

// This is the mock function which will replace the real function
@test:Mock {
    moduleName: "ballerina/io",
    functionName: "println"
}
public function mockPrint(any... s) {
    outputs[counter] = s[0];
    counter += 1;
}

@test:Config
function testFunc() {
    // Invoking the main function
    main();

    string out1 = "Output: Hello World.!!!";
    test:assertEquals(outputs[0], out1);
    test:assertEquals(outputs[1], out1);
    test:assertEquals(outputs[2], out1);
}
