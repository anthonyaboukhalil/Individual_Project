

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

 
public class synonymAPI extends gui {
	
	public static String[] synonyms(String word) {
		String hold = "";
		int i = 0;
		int beg = 0;
		int end = 0;
		String[] strArr = new String[100];
		
		//We use an HTTP GET request here in order to retrieve the synonyms we require from the API
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://www.dictionaryapi.com/api/v3/references/thesaurus/json/" + word + "?key=f2be3352-0863-45df-a88b-085a31690fea"))
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			hold = response.body();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			strArr[0] = "Error in code. Caught exception.";
			return strArr;
		}
		
		//This is to only get a substring of the result, as we're only needing the synonyms
		beg = hold.indexOf("syns");
		end = hold.indexOf("ants");
		
		hold = hold.substring(beg + 8, end - 4);
		
		//To check the synonyms that the API is putting out, include the code below:
			//System.out.println(hold);
		
		
		
		//This loop is to separate our synonyms into different array indexes
		while(hold.contains("\"")) {
			end = hold.indexOf("\"", 1);
			
			strArr[i] = hold.substring(1, end);
			
			try {
				hold = hold.substring(end + 2);
			} catch(StringIndexOutOfBoundsException e) {
				hold = hold.substring(end);
			}
			i++;
			
			
		//These exceptions/if statements are specific to the format of the string that we receive from this API, it will not function properly with other APIs attempting the same thing
			if(hold.contains("[") && hold.indexOf("[", 1) < 2) {
				hold = hold.substring(2);
			}
			if(hold.length() < 2)
				hold = "";
		}
		
		return strArr;
		
	}
	static String autoComplete(String city) throws IOException, InterruptedException{

        city = city.trim();
        city = city.replace(" ", "%20");
        var url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+ city +"&location=37.76999,-122.44696&radius=500&key=AIzaSyCdZfM02RChORXRczoOr8xP5IXFIo50LiI";
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build(); 
        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String description = response.body();
        int testB = description.indexOf("description") + 16;
        int testE = description.indexOf("\"",testB);
       
       return description.substring(testB, testE);
        
    }
	public static String findDoctor(String city) throws IOException, InterruptedException {
	
        city = city.trim();
        city = city.replace(" ", "%20");
        var url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="+city+"%20doctor&inputtype=textquery&fields=formatted_address,name,rating,opening_hours,geometry&key=AIzaSyCdZfM02RChORXRczoOr8xP5IXFIo50LiI";
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build(); 
        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String doctorName = response.body();
        String doctorAddress = response.body();

    int testB = doctorAddress.indexOf("address") + 12;
    int testE = doctorAddress.indexOf("\"",testB);

    int test2B = doctorName.indexOf("name") + 9;
    int test2E = doctorName.indexOf("\"",test2B);
        String address = doctorAddress.substring(testB, testE);
        String clinicName = doctorName.substring(test2B, test2E);
        return address + "\n" + clinicName;

    }
	public static String directions(String location, String destination) throws IOException, InterruptedException {
	
		location = location.trim().replace(" ", "+");
        destination =  destination.trim().replace(" ", "+");
         var url = "https://maps.googleapis.com/maps/api/directions/json?origin="+location+"&destination="+destination+"d&key=AIzaSyCdZfM02RChORXRczoOr8xP5IXFIo50LiI";
         var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build(); 
         var client = HttpClient.newBuilder().build();
         var response = client.send(request, HttpResponse.BodyHandlers.ofString());
         
             String distance = response.body();
             
             int testB = distance.indexOf("text") + 9;
             int testE = distance.indexOf("\"",testB);
             return distance.substring(testB, testE);

    }
	public static void main(String[] args){

		//This area of the code is just being used to test the API and ensuring the method works properly.
		
		String[] test = new String[100];
		int i = 0;
		
		test = synonyms("yes");
		
		
		while(test[i] != null) {
			System.out.println(test[i]);
			i++;
		}
	}
}