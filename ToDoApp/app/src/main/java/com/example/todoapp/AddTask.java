package com.example.todoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class AddTask extends BottomSheetDialogFragment {

    private TextView txttarihsec;
    private EditText txttask;
    private Button btngorevekle;
    private FirebaseFirestore firestore;
    private Context context;
    private String yapıldı="";
    private String id = "";
    private String yapıldıEkle = "";


    public static final String TAG = "AddTask";

    public static AddTask newInstance(){
        return new AddTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_task,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txttarihsec=view.findViewById(R.id.txttarihsec);
        txttask=view.findViewById(R.id.txttask);

        firestore=FirebaseFirestore.getInstance();

        boolean isUpdate = false;

        final Bundle bundle = getArguments();


        if (bundle != null){
            isUpdate = true;
            String gorev = bundle.getString("gorev");
            id = bundle.getString("id");
            yapıldıEkle = bundle.getString("due");

            txttask.setText(gorev);
            txttarihsec.setText(yapıldıEkle);

            if (gorev.length() > 0){
                btngorevekle.setEnabled(false);
                btngorevekle.setBackgroundColor(Color.GRAY);
            }
        }







        txttask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().equals("")){
                    btngorevekle.setEnabled(false);
                    btngorevekle.setBackgroundColor(Color.GRAY);

                }else{
                    btngorevekle.setEnabled(true);
                    //btngorevekle.setBackground(getResources().getColor(R.color.purple_200));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        txttarihsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar tariholustur=Calendar.getInstance();

                int AY= tariholustur.get(Calendar.MONTH);
                int GÜN= tariholustur.get(Calendar.DATE);
                int YIL= tariholustur.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yıl, int ay, int gun) {
                        txttarihsec.setText(gun +  "/"  +  ay+  "/"  +  yıl);
                        yapıldı=gun +  "/"  +  ay+  "/"  +  yıl;




                    }
                },YIL,AY,GÜN);
                datePickerDialog.show();

            }
        });







        boolean finalIsUpdate = isUpdate;

        btngorevekle=view.findViewById(R.id.btngorevekle);
        btngorevekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String gorev =txttask.getText().toString();

                if(finalIsUpdate){
                    firestore.collection("gorev").document(id).update("gorev" , gorev , "yapıldı" , yapıldı);
                    Toast.makeText(context, "Gorev Kaydedildi.", Toast.LENGTH_SHORT).show();
                }else {
                    if(gorev.isEmpty()){
                        Toast.makeText(context, "boş görev eklenemez", Toast.LENGTH_SHORT).show();
                    }else {
                        // if(gorev.isEmpty()){Toast.makeText(context, "Empty task not Allowed !!", Toast.LENGTH_SHORT).show();}else{}

                        Map<String,Object> taskmap =new HashMap<>();
                        taskmap.put("gorev",gorev);
                        taskmap.put("yapıldı",yapıldı);
                        taskmap.put("durum",0);
                        taskmap.put("time", FieldValue.serverTimestamp());

                        firestore.collection("gorev").add(taskmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context, "görev kaydedildi", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(context,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }


                }


                dismiss();
            }
        });
    }


    @Override
    public void onAttach (@NonNull Context context){
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        //Activity activity=getActivity();
        FragmentActivity activity = requireActivity();


        if(activity instanceof CloseListner){
            ((CloseListner)activity).closeL(dialog);

        }


    }
}
