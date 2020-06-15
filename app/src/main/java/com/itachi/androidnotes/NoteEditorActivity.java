package com.itachi.androidnotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;
    String oldText;
    String newText;

    @Override
    protected void onDestroy() {
        if (MainActivity.notes.get(noteId).isEmpty()) {
            MainActivity.notes.remove(noteId);
            MainActivity.arrayAdapter.notifyDataSetChanged();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.itachi.androidnotes", Context.MODE_PRIVATE);
            HashSet<String> set = new HashSet<>(MainActivity.notes);
            sharedPreferences.edit().putStringSet("notes", set).apply();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        final EditText editText = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        } else {
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.itachi.androidnotes", Context.MODE_PRIVATE);
            HashSet<String> set = new HashSet<>(MainActivity.notes);

            sharedPreferences.edit().putStringSet("notes", set).apply();
        }

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                oldText = String.valueOf(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newText = String.valueOf(charSequence);
                if (!oldText.equals(String.valueOf(charSequence))) {
                    MainActivity.notes.set(noteId, String.valueOf(charSequence));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.itachi.androidnotes", Context.MODE_PRIVATE);
                    HashSet<String> set = new HashSet<>(MainActivity.notes);
                    sharedPreferences.edit().putStringSet("notes", set).apply();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (MainActivity.notes.get(noteId).isEmpty()) {
                    MainActivity.notes.remove(noteId);
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.itachi.androidnotes", Context.MODE_PRIVATE);
                    HashSet<String> set = new HashSet<>(MainActivity.notes);
                    sharedPreferences.edit().putStringSet("notes", set).apply();
                }
            }
        });


    }

}
