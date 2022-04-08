package com.example.postapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton Fab;
    ConstraintLayout AddProduct;
    ImageView Close;
    EditText Category_Id,Tittle,VariantsAttributes,Desc;
    Button Submit;
    RequestQueue requestQeue;
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Assign
        Fab=findViewById(R.id.fab);
        AddProduct=findViewById(R.id.addproduct);
        Close=findViewById(R.id.close);
        Category_Id=findViewById(R.id.category_id);
        Tittle=findViewById(R.id.Title);
        VariantsAttributes=findViewById(R.id.Variants_attributes);
        Desc=findViewById(R.id.Desc);
        Submit=findViewById(R.id.submit);
        data=findViewById(R.id.data);

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProduct.setVisibility(View.VISIBLE);
                Fab.setVisibility(View.INVISIBLE);
            }
        });
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProduct.setVisibility(View.INVISIBLE);
                Fab.setVisibility(View.VISIBLE);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id,tittle,variant,desc;
                id=Get(Category_Id);
                tittle=Get(Tittle);
                variant=Get(VariantsAttributes);
                desc=Get(Desc);
                Toast.makeText(MainActivity.this, id+" "+tittle+" "+variant+" "+desc, Toast.LENGTH_SHORT).show();
                int tf=0;
                if(desc.isEmpty()){
                    ViewError(Desc,"Invalid");
                    tf++;
                }
                if(variant.isEmpty()){
                    ViewError(VariantsAttributes,"Invalid");
                    tf++;
                }
                if(tittle.isEmpty()){
                    ViewError(Tittle,"Invalid");
                    tf++;
                }
                if(isNumeric(id)==false)
                {
                  ViewError(Category_Id,"Invalid");
                  tf++;
                }

                if(tf>0){
                  //  return;

                }
                String url=" https://itmagic.app/api/insales/categories.php";
                StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                        response -> ok(response),
                        error -> Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show()){

                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError{
                        Map<String,String> params=new HashMap<>();
                        params.put("shop_name","myshop-bsq158");
                        params.put("email","asatarpk@gmail.com");
                        params.put("password","Sattar_786");
                        return params;
                    }

                };
                requestQeue= Volley.newRequestQueue(MainActivity.this);
                requestQeue.add(stringRequest);


            }
        });
    }

    private void ok(String response) {
        data.setText(response.toString());

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    private void ViewError(EditText editText, String invalid) {
        editText.setError(invalid);
        editText.requestFocus();

    }

    private void ViewNull(EditText editText) {
        editText.setText("");
    }

    private String Get(EditText editText) {
        return editText.getText().toString();
    }
}