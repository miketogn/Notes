package com.miketucker.notes;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miketucker.notes.models.Note;
import com.miketucker.notes.persistence.NoteRepository;
import com.miketucker.notes.util.Utility;

public class NoteActivity extends AppCompatActivity  implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher
{
    
    private static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    private LineEditText lineEditText;
    private EditText editTitle;
    private TextView viewTitle;
    private RelativeLayout checkContainer, backArrowContainer;
    private ImageButton check, backArrow;

    private boolean isNewNote;
    private Note initialNote;
    private GestureDetector gestureDetector;
    private int mode;
    private NoteRepository noteRepository;
    private Note finalNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        lineEditText = findViewById(R.id.note_text);
        editTitle = findViewById(R.id.note_edit_title);
        viewTitle = findViewById(R.id.note_text_title);
        checkContainer = findViewById(R.id.check_container);
        backArrowContainer = findViewById(R.id.back_arrow_container);
        check = findViewById(R.id.toolbar_check);
        backArrow = findViewById(R.id.toolbar_back_arrow);

        noteRepository = new NoteRepository(this);

        if(getIncomingIntent()){
            //this is a new note
            setNewNoteProperties();
            enableEditMode();
        }
        else {
            // this is NOT a new note
            setNoteProperties();
            disableContentInteraction();
        }

        setListeners();

    }

    private void setListeners(){
        lineEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, this);
        viewTitle.setOnClickListener(this);
        check.setOnClickListener(this);
        backArrow.setOnClickListener(this);
        editTitle.addTextChangedListener(this);
    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_note")){
            initialNote = getIntent().getParcelableExtra("selected_note");

            finalNote = new Note();
            finalNote.setTitle(initialNote.getTitle());
            finalNote.setContent(initialNote.getContent());
            finalNote.setTimestamp(initialNote.getTimestamp());
            finalNote.setId(initialNote.getId());

            mode = EDIT_MODE_DISABLED;
            isNewNote = false;
            return false;
        }
        mode = EDIT_MODE_ENABLED;
        isNewNote = true;
        return true;
    }

    // save changes

    private void saveChanges(){
        if(isNewNote){
            saveNewNote();
        }
        else {
            updateNote();
        }
    }

    // Update note

    private void updateNote(){
        noteRepository.updateNote(finalNote);
    }

    // Save new note

    private void saveNewNote(){
        noteRepository.insertNoteTask(finalNote);
    }

    private void disableContentInteraction(){
        lineEditText.setKeyListener(null);
        lineEditText.setFocusable(false);
        lineEditText.setFocusableInTouchMode(false);
        lineEditText.setCursorVisible(false);
        lineEditText.clearFocus();
    }

    private void enableContentInteraction(){
        lineEditText.setKeyListener(new EditText(this).getKeyListener());
        lineEditText.setFocusable(true);
        lineEditText.setFocusableInTouchMode(true);
        lineEditText.setCursorVisible(true);
        lineEditText.requestFocus();
    }

    private void enableEditMode(){
        backArrowContainer.setVisibility(View.GONE);
        checkContainer.setVisibility(View.VISIBLE);

        viewTitle.setVisibility(View.GONE);
        editTitle.setVisibility(View.VISIBLE);

        mode = EDIT_MODE_ENABLED;

        enableContentInteraction();
    }

    private void disableEditMode(){
        backArrowContainer.setVisibility(View.VISIBLE);
        checkContainer.setVisibility(View.GONE);

        viewTitle.setVisibility(View.VISIBLE);
        editTitle.setVisibility(View.GONE);

        mode = EDIT_MODE_DISABLED;

        disableContentInteraction();

        // compare final note to initial note

        String temp = lineEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if(temp.length() > 0){
            finalNote.setTitle(editTitle.getText().toString());
            finalNote.setContent(lineEditText.getText().toString());
            String timestamp = Utility.getCurrentTimeStamp();
            finalNote.setTimestamp(timestamp);

            if(!finalNote.getContent().equals(initialNote.getContent())
            || !finalNote.getTitle().equals(initialNote.getTitle())){
                saveChanges();
            }
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if(view == null){
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setNoteProperties(){
        viewTitle.setText(initialNote.getTitle());
        editTitle.setText(initialNote.getTitle());
        lineEditText.setText(initialNote.getContent());
    }

    private void setNewNoteProperties(){
        viewTitle.setText("Note Title");
        editTitle.setText("Note Title");

        initialNote = new Note();
        finalNote = new Note();
        initialNote.setTitle("Note Title");
        finalNote.setTitle("Note Title");
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: double tapped!");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.toolbar_check:{
                hideSoftKeyboard();
                disableEditMode();
                break;
            }

            case R.id.note_text_title:{
                enableEditMode();
                editTitle.requestFocus();
                editTitle.setSelectAllOnFocus(true);
                break;
            }

            case R.id.toolbar_back_arrow:{
                finish();
                break;
            }
        }
    }

    // Set correct action for back button

    @Override
    public void onBackPressed() {
        if(mode == EDIT_MODE_ENABLED){
            onClick(check);
        }
        else {
            super.onBackPressed();
        }
    }

    // save instance state

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mode);
    }

    // restore instance state

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mode = savedInstanceState.getInt("mode");
        if(mode == EDIT_MODE_ENABLED){
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        viewTitle.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
