package com.softz.simuladorcredito;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // Arreglo (HardCore)
    String [] atypeCredit = {"Vivienda","Educación","Libre Inversión"};
    String selTypeCredit;
    // Instanciar objetos que referencian cada id del archivo activity_main.xml
    /* EditText Ident, Fullname, Monto;
    Spinner CreditType;
    RadioButton rb12, rb24, rb36;
    Switch Fees;
    TextView ValorCuota, TotalDeuda;
    Button Calcular, Limpiar; */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Referencia los objetos con cada id del archivo xml
        EditText Ident = findViewById(R.id.etIdent);
        EditText Fullname = findViewById(R.id.etFullname);
        EditText Monto = findViewById(R.id.etMonto);
        Spinner CreditType = findViewById(R.id.spCreditType);
        RadioButton rb12 = findViewById(R.id.rb12Month);
        RadioButton rb24 = findViewById(R.id.rb24Month);
        RadioButton rb36 = findViewById(R.id.rb36Month);
        TextView ValorCuota = findViewById(R.id.tvValorCuota);
        TextView TotalDeuda = findViewById(R.id.tvTotalDeuda);
        Button Calcular = findViewById(R.id.btnCalculate);
        Button Limpiar = findViewById(R.id.btnClean);
        Switch Fees = findViewById(R.id.swFees);

        // Array --> ArrayAdapter( ) --> Spinner

        // llenar el ArrayAdapter con el array atypeCredit
        ArrayAdapter adpCreditType = new ArrayAdapter(this, android.R.layout.simple_list_item_checked,atypeCredit);

        // Asignar añ spinner, adaptador del array
        CreditType.setAdapter((adpCreditType));

        // Generar el evento para la opción selecionada del tipo de credito
        CreditType.setOnItemSelectedListener(this);

        // Generar evento para el boton limpiar
        Limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Limpiar los datos de: ident, fullname, monto, valorcuota, totaldeuda.
                Ident.setText("");
                Fullname.setText("");
                Monto.setText("");
                ValorCuota.setText("");
                TotalDeuda.setText("");
                Fees.setChecked(false);
                Ident.requestFocus();// El foco se ubica en este lugar.
            }
        });

        // Generar evento para el boton de calcular
         Calcular.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // Chequear todos los daros que se haya digitado la Ident, Fullname, Monto
                 if (!Ident.getText().toString().isEmpty() && !Fullname.getText().toString().isEmpty() && !Monto.getText().toString().isEmpty()){
                     // De vuelve un dato boooleano, true o false
                     // Chequear el monto que este entre 1 millón y 100 millones
                     double xMonto = parseDouble(Monto.getText().toString());
                     if (xMonto >= 1000000 && xMonto <= 1000000000){
                         // Chequear el tipo de credito seleccionado
                         double rate = 0;
                         switch (selTypeCredit){
                             case "Vivienda":
                                 rate = 0.01;
                                 break;
                             case "Educación":
                                 rate = 0.005;
                                 break;
                             case "Libre Inversión":
                                 rate = 0.02;
                                 break;
                         }
                         // Chequear el numero de cuotas seleccionada
                         double duesNumber = 12;
                         if (rb12.isChecked()){
                             duesNumber = 12;
                         }
                         if (rb24.isChecked()){
                             duesNumber = 24;
                         }
                         if (rb36.isChecked()){
                             duesNumber = 36;
                         }
                         double share = 0;
                         if (Fees.isChecked()){
                             share = 10000;
                         }
                         // Calcular el total de la deuda
                         double xtotaldeuda = xMonto + (xMonto * rate * duesNumber);
                         double xvalorcuota = xtotaldeuda / duesNumber;
                         // Asignar xtotaldeuda (variable) al elemento referencia totaldeuda
                         // Generar un objeto para dar presentacion a los numeros
                         DecimalFormat nroformat = new DecimalFormat("###,###,###,#");

                         TotalDeuda.setText(nroformat.format(xtotaldeuda));
                         ValorCuota.setText(nroformat.format(xvalorcuota+share));
                     }else{
                         Toast.makeText(getApplicationContext(),"El monto debe estar entre 1 millón y 100 millones",Toast.LENGTH_LONG).show();
                     }
                 }else{
                     Toast.makeText(getApplicationContext(),"Debe ingresar los datos correspondientes de cada campo",Toast.LENGTH_LONG).show();
                 }
             }
         });
    }

    // Metodos de Sobrecargado
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       selTypeCredit = atypeCredit[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}