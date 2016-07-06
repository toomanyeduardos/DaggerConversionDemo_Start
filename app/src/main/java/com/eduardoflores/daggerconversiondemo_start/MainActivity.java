package com.eduardoflores.daggerconversiondemo_start;

import com.eduardoflores.daggerconversiondemo_start.model.InitialResponse;
import com.eduardoflores.daggerconversiondemo_start.service.Service;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    // json url to download and parse
    // https://api.myjson.com/bins/4lqo7
    @BindView(R.id.main_text)
    TextView mainText;

    public static String LOG_TAG = "DaggerDemo";

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        // Don't forget to bind butterknife!
        ButterKnife.bind(this);

        mainText.setText(getString(R.string.text_waiting));

        // make network call
        Service service = new Service();
        Call<InitialResponse> call =  service.getWidget();
        call.enqueue(new Callback<InitialResponse>() {
            @Override
            public void onResponse(Call<InitialResponse> call, Response<InitialResponse> response)
            {
                Log.i(LOG_TAG, "onResponse. Downloaded and parsed data correctly!");

                // this is a sample output to device, and it is horrible.
                // please don't every do this. I'm only doing it because it is irrelevant in
                // relationship to dagger.
                String output = response.body().widget.window.title + "\n\n\n\n";
                output = output + context.getString(R.string.text_image_source) + " " + response.body().widget.image.src + "\n\n";
                output = output + context.getString(R.string.text_on_mouse_up) + " " + response.body().widget.text.onMouseUp;

                mainText.setText(output);
            }

            @Override
            public void onFailure(Call<InitialResponse> call, Throwable t) {
                Log.i(LOG_TAG, "onFailure. Either download failed, or data parsing failed. Error = " + t.getMessage());
            }
        });
    }
}
