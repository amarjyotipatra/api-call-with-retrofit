package com.example.retrofitapicall;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/user")
public class Main {

    private final GitHubService service;

    public Main() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(GitHubService.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/{username}/repos")
    public String getUserRepos(@PathVariable String username) {
        Call<List<Repo>> reposCall = service.listRepos(username);
        Response<List<Repo>> response;
        try {
            response = reposCall.execute();
        } catch (IOException e) {
            return "<html><body><h1>Error fetching repositories</h1><p>" + e.getMessage() + "</p></body></html>";
        }

        if (!response.isSuccessful() || response.body() == null) {
            return "<html><body><h1>Error fetching repositories. HTTP Code: " + response.code() + "</h1></body></html>";
        }

        List<Repo> repos = response.body();
        StringBuilder html = new StringBuilder();
        html.append("<html>")
                .append("<head><title>GitHub Repositories</title></head>")
                .append("<body>")
                .append("<h1>Repositories for '").append(username).append("'</h1>")
                .append("<ol>");
        for (Repo repo : repos) {
            html.append("<li>").append(repo.getName()).append("</li>");
        }
        html.append("</ol>")
                .append("</body>")
                .append("</html>");

        return html.toString();
    }

    interface GitHubService {
        @GET("users/{user}/repos")
        Call<List<Repo>> listRepos(@Path("user") String user);
    }

    @Getter
    @Setter
    static class Repo {
        private String name;
    }
}
