// Jose Igualada Calero //

package dam2.ejercicioandroidrepaso_jose_igualada_calero;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

public class MasDatos extends Activity {

    EditText textnombre;
    Spinner spnProvincia,spnLocalidad;
    Switch swNotificaciones;
    Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mas_datos);

        //Recogemos los objetos de la Activity
        textnombre = (EditText) findViewById(R.id.textNombre);

        spnProvincia = (Spinner) findViewById(R.id.spnProvincia);
        spnLocalidad = (Spinner) findViewById(R.id.spnLocalidad);
        swNotificaciones = (Switch) findViewById(R.id.swNotificaciones);
        rellenoSpinner();

        btnContinuar = (Button) findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swNotificaciones.isChecked()){

                    notificacion();
                    guardarDatos();
                    finish();

                }else{

                    guardarDatos();
                    finish();

                }
            }
        });


    }

    public void rellenoSpinner(){

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.provincias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnProvincia .setAdapter(adapter);
        spnProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypedArray loc = getResources().obtainTypedArray(R.array.array_provincia_a_localidades);
                String localidades[] = getResources().getStringArray(loc.getResourceId(position, 0));

                ArrayAdapter adapter1 = new ArrayAdapter(MasDatos.this, android.R.layout.simple_spinner_item, localidades);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spnLocalidad.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void notificacion(){

        //Creacion de la notificación
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle("Alert");
        builder.setContentText("¿Desea continuar?");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.alert));
        builder.setSmallIcon(R.drawable.info);

        //Estilo de notificación
        Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
        inboxStyle.addLine(textnombre.getText());
        inboxStyle.addLine(spnProvincia.getSelectedItem().toString());
        inboxStyle.addLine(spnLocalidad.getSelectedItem().toString());
        builder.setStyle(inboxStyle);

        //Accion de notificación
        Bundle b = new Bundle();

        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.btn_continuar, "Continuar", pendingIntent);

        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.btn_reiniciar, "Reiniciar", pendingIntent1);

        intent.putExtras(b);
        intent1.putExtras(b);
        builder.setContentIntent(pendingIntent);
        builder.setContentIntent(pendingIntent1);

        //Lanzamos
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(2, builder.build());

    }

    public void guardarDatos(){
        Bundle b = new Bundle();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        b.putString("nombre", ""+textnombre.getText());
        b.putString("provincia", spnProvincia.getSelectedItem().toString());
        b.putString("localidad", spnLocalidad.getSelectedItem().toString());
        intent.putExtras(b);
        //startActivityForResult(intent, 0);
    }
}
