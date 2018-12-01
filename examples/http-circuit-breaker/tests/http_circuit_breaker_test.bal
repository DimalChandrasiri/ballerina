import ballerina/test;
import ballerina/io;
import ballerina/http;

boolean serviceStarted;

function startService() {
    //serviceStarted = test:startServices("http-circuit-breaker");
}

@test:Config {
    before: "startService",
    after: "stopService"
}
function testFunc() {
    // Invoking the main function.
    http:Client httpEndpoint = new("http://localhost:9090");
    // Check whether the server has started.
    //test:assertTrue(serviceStarted, msg = "Unable to start the service");

    string responseString = "Hello World!!!";
    // Send a GET request to the specified endpoint
    var response1 = httpEndpoint->get("/cb");
    if (response1 is http:Response) {
        var result = textResponse.getTextPayload();
        if (result is string) {
            test:assertEquals(result, responseString);
        } else {
            test:assertFail(msg = "Invalid response message:");
        }
    } else {
        test:assertFail(msg = "Failed to call the endpoint:");
    }

    // Send a GET request to the specified endpoint.
    var response2 = httpEndpoint->get("/cb");
    if (response2 is http:Response) {
        var result = textResponse.getTextPayload();
        if (result is string) {
            test:assertEquals(result, responseString);
        } else {
            test:assertFail(msg = "Invalid response message:");
        }
    } else {
        test:assertFail(msg = "Failed to call the endpoint:");
    }

    // Send a GET request to the specified endpoint.
    var response3 = httpEndpoint->get("/cb");
    if (response3 is http:Response) {
        var result = textResponse.getTextPayload();
        if (result is string) {
            test:assertEquals(result, "Internal error occurred while processing the request.");
        } else {
            test:assertFail(msg = "Invalid response message:");
        }
    } else {
        test:assertFail(msg = "Failed to call the endpoint:");
    }

    // Send a GET request to the specified endpoint.
    var response4 = httpEndpoint->get("/cb");
    if (response4 is http:Response) {
        var result = textResponse.getTextPayload();
    } else {
        test:assertFail(msg = "Failed to call the endpoint:");
    }

    // Send a GET request to the specified endpoint.
    var response5 = httpEndpoint->get("/cb");
    if (response5 is http:Response) {
        var result = textResponse.getTextPayload();
    } else {
        test:assertFail(msg = "Failed to call the endpoint:");
    }

    io:println("Reached");

    // Send a GET request to the specified endpoint.
    var response6 = httpEndpoint->get("/cb");
    if (response6 is http:Response) {
        var result = textResponse.getTextPayload();
    } else {
        test:assertFail(msg = "Failed to call the endpoint:");
    }
}

function stopService() {
    //test:stopServices("http_circuit_breaker");
}
