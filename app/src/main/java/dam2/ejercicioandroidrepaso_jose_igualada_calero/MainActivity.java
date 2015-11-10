// Jose Igualada Calero //
package dam2.ejercicioandroidrepaso_jose_igualada_calero;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView txtContador, txtNombre, txtProvincia, txtLocalidad;
    Button btnContador;
    int cont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cont = 0;
        //Recogemos los objetos de la Activity

        txtContador = (TextView) findViewById(R.id.txtContador);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtProvincia = (TextView) findViewById(R.id.txtProvincia);
        txtLocalidad = (TextView) findViewById(R.id.txtLocalidad);
        btnContador = (Button) findViewById(R.id.btnContador);
        txtContador.setText("Pulsación número: "+cont);
        btnContador.setOnClickListener(new View.OnClickListener() {

            //Incrementamos el contador 1, cada vez que pulsamos
            @Override
            public void onClick(View v) {
                cont++;
                txtContador.setText("Pulsación número: " + cont );

                //Emitimos notificación

                //Construimos
                Notification.Builder builder = new Notification.Builder(getApplicationContext());
                builder.setContentTitle("¡Has pulsado!");
                builder.setContentText("Has pulsado " + cont + " veces");
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.alert));
                builder.setSmallIcon(R.drawable.info);

                //Lanzamos
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());

                //Cuando lleguemos a 5, pasamos a la actividad Activity MasDatos
                if(cont == 5) {
                    Intent MasDatos = new Intent(getApplicationContext(), dam2.ejercicioandroidrepaso_jose_igualada_calero.MasDatos.class);
                    startActivity(MasDatos);
                    finish();
                }
            }
        });

        Bundle b = getIntent().getExtras();

        if(b != null) {
            txtNombre.setText("Hola " + b.getString("nombre"));
            txtProvincia.setText("De " + b.getString("provincia"));
            txtLocalidad.setText("Exactamente de " + b.getString("localidad"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
