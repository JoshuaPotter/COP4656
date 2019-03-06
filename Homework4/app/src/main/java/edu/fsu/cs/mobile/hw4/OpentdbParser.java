//OpentdbParser
package edu.fsu.cs.mobile.hw4;

import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class OpentdbParser {

    public static String SAMPLE_ITEMS = "{\"response_code\":0,\"results\":[{\"category\":\"Geography\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"Which of these countries is &quot;doubly landlocked&quot; (surrounded entirely by one or more landlocked countries)?\",\"correct_answer\":\"Uzbekistan\",\"incorrect_answers\":[\"Switzerland\",\"Bolivia\",\"Ethiopia\"]},{\"category\":\"History\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"Which of the following ancient Near Eastern peoples still exists as a modern ethnic group?\",\"correct_answer\":\"Assyrians\",\"incorrect_answers\":[\"Babylonians\",\"Hittites\",\"Elamites\"]},{\"category\":\"Vehicles\",\"type\":\"multiple\",\"difficulty\":\"easy\",\"question\":\"Which of the following car manufacturers had a war named after it?\",\"correct_answer\":\"Toyota\",\"incorrect_answers\":[\"Honda\",\"Ford\",\"Volkswagen\"]},{\"category\":\"Vehicles\",\"type\":\"multiple\",\"difficulty\":\"easy\",\"question\":\"Which car tire manufacturer is famous for its &quot;Eagle&quot; brand of tires, and is the official tire supplier of NASCAR?\",\"correct_answer\":\"Goodyear\",\"incorrect_answers\":[\"Pirelli\",\"Bridgestone\",\"Michelin\"]},{\"category\":\"General Knowledge\",\"type\":\"multiple\",\"difficulty\":\"hard\",\"question\":\"In flight systems, what does the initialism &quot;TCAS&quot; stand for?\",\"correct_answer\":\"Traffic Collision Avoidance System\",\"incorrect_answers\":[\"Traffic Communication Alert System\",\"Traffic Configuration Alignment System\",\"Traffic Call-sign Abbreviation System\"]}]}";

    /**
     * Parse json results from opentdb into ArrayList of TriviaItem.
     * This code requires you to first implement TriviaItem class.
     * <p>
     * Usage: ArrayList<TriviaItem> items = OpentdbParser.parseTriviaItems(response);
     *
     * @param response String response containing json results from opentdb
     * @return ArrayList<TriviaItem>
     * @throws JSONException occurs if parser screws up
     */
    static ArrayList<TriviaItem> parseTriviaItems(String response) throws JSONException {
        ArrayList<TriviaItem> items = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject object = results.getJSONObject(i);
            String category = Html.fromHtml(object.getString("category")).toString();
            String type = Html.fromHtml(object.getString("type")).toString();
            String difficulty = Html.fromHtml(object.getString("difficulty")).toString();
            String question = Html.fromHtml(object.getString("question")).toString();
            String correctAnswer = Html.fromHtml(object.getString("correct_answer")).toString();
            ArrayList<String> incorrectAnswers = new ArrayList<>();
            JSONArray answers = object.getJSONArray("incorrect_answers");
            for (int j = 0; j < answers.length(); j++) {
                incorrectAnswers.add(Html.fromHtml(answers.getString(j)).toString());
            }
            items.add(new TriviaItem(category, type, difficulty, question, correctAnswer,
                    incorrectAnswers));
        }
        return items;
    }
}