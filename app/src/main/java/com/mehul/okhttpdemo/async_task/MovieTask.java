package com.mehul.okhttpdemo.async_task;

import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehul.okhttpdemo.MainActivity;
import com.mehul.okhttpdemo.model.Movie;
import com.mehul.okhttpdemo.utils.NetworkUtils;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Micky on 11/12/2017.
 */

public class MovieTask extends AsyncTask<String,Void,List<Movie>> {
    List<Movie> movieList = new ArrayList<>();

    private MovieTaskEvent movieTaskEvent;

    public interface MovieTaskEvent{

        void onMovieTaskResult(List<Movie> movieList);
    }

    public MovieTask(MovieTaskEvent movieTaskEvent) {
        this.movieTaskEvent = movieTaskEvent;
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {


        int count = 0;
        boolean retry = false;
        StringBuilder responseStrBuilder = new StringBuilder();

        do {
            retry = false;
            try {

                NetworkUtils.doNetworkProcessGet(strings[0], responseStrBuilder);
                String response = responseStrBuilder.toString();
                ObjectMapper objectMapper = new ObjectMapper();

                JavaType type = objectMapper.getTypeFactory().
                        constructCollectionType(List.class, Movie.class);
                movieList = objectMapper.readValue(response, type);


                //movieList = objectMapper.readValues(response,new TypeReference<List<Movie>>() {});
                return movieList;

            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            retry = true;

            count += 1;

        } while (count < 3 && retry);


        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {
        super.onPostExecute(movieList);

        if (movieTaskEvent != null){
            movieTaskEvent.onMovieTaskResult(movieList);
        }
    }
}
