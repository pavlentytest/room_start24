package ru.samsung.itschool.mdev.roomvsfragment;

import static ru.samsung.itschool.mdev.roomvsfragment.fragment.TaskDialogFragment.TAG_DIALOG_TASK_SAVE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.samsung.itschool.mdev.roomvsfragment.adapter.TaskAdapter;
import ru.samsung.itschool.mdev.roomvsfragment.db.DBClient;
import ru.samsung.itschool.mdev.roomvsfragment.db.Task;
import ru.samsung.itschool.mdev.roomvsfragment.fragment.TaskDialogFragment;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showDialog());
        Log.d("RRRR","onCreate()");

        Disposable disposable = DBClient.getInstance(this)
                .getAppDatabase()
                .taskDao()
                .getAll()
                // поток интерфейса UI наблюдает за Flowable данными
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                    TaskAdapter adapter = new TaskAdapter(MainActivity.this, tasks);
                    recyclerView.setAdapter(adapter);
                });

        //TaskAdapter adapter = new TaskAdapter(MainActivity.this, tasks);
        //recyclerView.setAdapter(adapter);

    }

    private void showDialog() {
        DialogFragment dialogFragment = new TaskDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_TASK_SAVE);
    }
}