package kr.pe.lightvil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import kr.pe.lightvil.daos.CallLogDAO;
import kr.pe.lightvil.models.EyePhoneDatabase;
import kr.pe.lightvil.repositories.CallLogRepository;

public class MainActivity extends AppCompatActivity {

    CallLogRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new CallLogRepository(getApplicationContext());
    }

    public CallLogRepository getRepository() {
        return repository;
    }
}