package fiot.general;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONObject;

public class ChatGPT {
    String bearer = "";
    public String openAiDavinci(String text) throws Exception {
        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer "+bearer);

        JSONObject data = new JSONObject();
        data.put("model", "text-davinci-003");
        data.put("prompt", text);
        data.put("max_tokens", 4000);
        data.put("temperature", 1.0);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();

        String answer = new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");
        return answer;
    }
    public String chatGPT(String text) throws Exception {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer "+bearer);

        JSONObject data = new JSONObject();
        JSONObject message = new JSONObject();
        message.put("content", text);
        message.put("role","user");

        ArrayList listaux = new ArrayList();
        listaux.add(message);

        //gpt-3.5-turbo
        data.put("model", "gpt-4");
        data.put("messages", listaux);

        //data.put("role", "user");
        //data.put("max_tokens", 4000);
        data.put("temperature", 1.0);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();

        String answer = new JSONObject(output).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content"); //.getString("message"));

        return answer;
    }

//    public static void main(String[] args) throws Exception {
//        //openAiDavinci("Hello, how are you?");
//        ChatGPT chatGPT = new ChatGPT();
//        String agentType = "buyer";
//        String agentName = "Agent1";
//        String prompt = "As a "+ agentType+ " agent, " +
//                "you're in a simulation with other agents. " +
//                "Agents 1, 2, and 3 are sellers; " +
//                "4 and 5 are buyers. " +
//                "You're agent " + agentName +" . " +
//                "The winner are the seller with the most money and the buyer who has more books. " +
//                "Each seller has 5 books and can send 5 messages. " +
//                "The simulation concludes after all messages are sent, all books are sold or if " +
//                "the buyers do not have money anymore. " +
//                "If you don't want to message, say 'No'. To message a specific agent, " +
//                "say \"Agent{id}: msg…\". " +
//                "To alter the book's price, include that action in your message.\n";
//        String answer = chatGPT.chatGPT(prompt);
//        System.out.println(answer);
//    }
}