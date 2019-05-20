package com.example.instrukcja4;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.instrukcja4.tasks.TaskListContent;

import java.util.Random;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskAddingFragment extends Fragment {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    final Random rnd = new Random();


    public TaskAddingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_task_adding, container, false);
        Button addBook = (Button) view.findViewById(R.id.button2);
        super.onActivityCreated(savedInstanceState);

        final Intent data = getActivity().getIntent();
        final String path = data.getStringExtra("photo");

        addBook.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EditText bookTitle = (EditText) view.findViewById(R.id.editText);
                EditText bookAuthor = (EditText) view.findViewById(R.id.editText2);
                EditText bookDate = (EditText) view.findViewById(R.id.editText3);
                String cover;

                if(path.contains("random")){
                    cover = "drawable " + rnd.nextInt(4);
                }else{
                    cover = path;
                }

                String Title = bookTitle.getText().toString();
                String Author = bookAuthor.getText().toString();
                String Date = bookDate.getText().toString();

                if(Title.isEmpty() && Author.isEmpty() && Date.isEmpty()){
                    TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1,
                            getString(R.string.pantadeusz),
                            getString(R.string.mickiewicz),
                            getString(R.string.dattatadeusza),
                            cover));
                }else{
                    if(Title.isEmpty()){
                        Title = getString(R.string.pantadeusz);
                    }
                    if(Author.isEmpty()){
                        Author = getString(R.string.mickiewicz);
                    }
                    if (Date.isEmpty()) {
                        Date = getString(R.string.dattatadeusza);
                    }
                    TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1,
                            Title,
                            Author,
                            Date,
                            cover));
                }
                bookTitle.setText("");
                bookAuthor.setText("");
                bookDate.setText("");

                String temp =" ";
                Intent data = new Intent();
                data.putExtra(MainActivity.temp,temp);
                //Set the result code for the MainActivity and attach the data Intent
                getActivity().setResult(RESULT_OK, data);

                getActivity().finish();

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            FragmentActivity holdingActivity = getActivity();
            if (holdingActivity instanceof MainActivity) {
                ((TaskFragment) holdingActivity.getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
            } else if (holdingActivity instanceof TaskInfoActivity) {
                ((TaskInfoActivity) holdingActivity).setImgChanged(true);
            }
        }
    }

}
