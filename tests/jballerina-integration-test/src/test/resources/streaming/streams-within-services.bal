import ballerina/http;
import ballerina/mime;
import ballerina/io;

string alertedHostName = "NotAssigned";

type ClientRequest record {
    string host;
};

type RequestCount record {
    string host;
    int count;
};

stream<ClientRequest> requestStream;

function initRealtimeRequestCounter () {

    stream<RequestCount> requestCountStream;

    //Whenever requestCountStream receives an event from the streaming rules defined in the forever block,
    //'printRequestCount' function will be invoked.
    requestCountStream.subscribe(printRequestCount);

    //Gather all the events which are coming to requestStream for 5 sec, then group by host and the count the number
    //of requests per host, then check if the count is more than 6. If so, publish the output (host and the count) to
    //requestCountStream. This forever block will be executed once, when initializing the service. So each time the
    //requestStream receives an event, the processing will happen asynchronously.
    forever {
        from requestStream
        window timeBatch(5000)
        select host, count(host) as count group by host having count > 6
        => (RequestCount [] counts) {
            //'counts' are the output of the streaming rules and those are published to requestCountStream.
            //Select clause should match with the structure of the 'RequestCount' struct.
            foreach var c in counts {
                requestCountStream.publish(c);
            }
        }
    }
}

function printRequestCount (RequestCount reqCount) {
    io:println("ALERT!! : Received more than 6 requests within 5 second from the host: " + reqCount.host);
    alertedHostName = reqCount.host;
}

endpoint http:Listener storeServiceEndpoint {
    port:9090
};

future ftr = start initRealtimeRequestCounter();

@http:ServiceConfig {
    basePath:"/"
}
service StoreService bind storeServiceEndpoint {

    @http:ResourceConfig {
        methods:["POST"],
        path:"/requests"
    }
    requests (endpoint conn, http:Request req) {
        string hostName = untaint req.getHeader("Host");
        ClientRequest clientRequest = {host : hostName};
        requestStream.publish(clientRequest);

        http:Response res = new;
        res.setJsonPayload("{'message' : 'request successfully received'}");
        checkpanic conn->respond(res);
    }

    @http:ResourceConfig {
        methods:["GET"],
        path:"/hosts"
    }
    hosts (endpoint conn, http:Request req) {
        http:Response res = new;
        res.setJsonPayload("{'message' : '" + alertedHostName + "'}");
        checkpanic conn->respond(res);
    }
}

