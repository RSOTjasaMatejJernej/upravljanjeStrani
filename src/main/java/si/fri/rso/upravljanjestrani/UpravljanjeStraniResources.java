
package si.fri.rso.upravljanjestrani;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.logs.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Inject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.GenericType;

import static javax.swing.UIManager.get;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("upravljanjeStrani")
@RequestScoped
public class UpravljanjeStraniResources {

    private Logger log = LogManager.getLogger(UpravljanjeStraniResources.class.getName());
    private Client httpClient;

    @Inject
    @DiscoverService("katalogStrani")
    private String baseUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }

    @GET
    public Response getAllStranis() {
        return Response.ok("test").build();

    }

    @GET
    @Path("{stranId}")
    public Stran getCustomer(@PathParam("stranId") String stranId) {
        log.debug(baseUrl + "/v1/katalogStrani?" + stranId);

       try {
           WebTarget wt = httpClient.target(baseUrl + "/v1/katalogStrani/" + stranId);
           Invocation.Builder b = wt.request();
           Stran response = b.get(new GenericType<Stran>() {
           });
           System.out.println("response je: " + response.toString());

            return response;
        } catch (Exception e) {
            //log.error(e);
            throw e;
        }}/*
        try {
            String response = sendGet(stranId);

            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok("exception").build();
        }*/


    private String sendGet(String stranId) throws Exception {



        URL obj = new URL(baseUrl+ "/v1/katalogStrani?" + stranId);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + baseUrl);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        return response.toString();

    }

    @POST
    public Response addNewStran(Stran stran) {
        Database.addStran(stran);
        return Response.ok(stran).build();
    }

    @DELETE
    @Path("{stranId}")
    public Response deleteStran(@PathParam("stranId") String stranId) {
        Database.deleteStran(stranId);
        return Response.ok(Response.Status.OK).build();
    }
}
