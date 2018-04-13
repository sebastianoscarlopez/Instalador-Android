package ar.com.oca.instaladorappcontenedor;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    final String packageName = "ar.com.oca.contenedores";
    int versionCode = 0;
    private Button btnContinuar;
    private TextView mensaje;
    static Boolean actualizado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnContinuar = findViewById(R.id.btnContinuar);
        mensaje = findViewById(R.id.mensaje);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!actualizado) {
            actualizado = true;
            comprobarVersion();
        }
    }

    synchronized public void comprobarVersion(){
        try {
            versionCode = 0;
            PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        String texto;
        switch (versionCode)
        {
            case 14:
                texto = "Desinstalar versión actual de App Contenedores";
                break;
            case 15:
                texto = "Desinstalar esta app auxiliar de instalación";
                break;
            default:
                texto = "App Contenedores sin instalar\r\nSiguiente paso instalar App Contenedores";
                break;
        }
        mensaje.setText(texto);
        btnContinuar.setEnabled(true);
    }

    public void btnContinuar_click(View view) {
        actualizado = false;

        Uri packageUri;
        Intent intent;
        switch (versionCode)
        {
            case 0:
                packageUri = Uri.parse(String.format("package:%s", packageName));
                intent = new Intent(Intent.ACTION_INSERT,packageUri);
                startActivityForResult(intent, 1);
                break;
            case 14:
                packageUri = Uri.parse(String.format("package:%s", packageName));
                intent = new Intent(Intent.ACTION_DELETE,packageUri);
                startActivity(intent);
                break;
            case 15:
                packageUri = Uri.parse(String.format("package:%s", getPackageName()));
                intent = new Intent(Intent.ACTION_INSERT,packageUri);
                startActivityForResult(intent, 1);
                break;
        }
    }
}
