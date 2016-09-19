package favarin.com.br.pernetas2.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Date;

import favarin.com.br.pernetas2.R;

public class ActivityCadastrarJogador extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtNomeJogador;
    private TextInputLayout txtNomeJogador;
    private Button btnSalvar;
    private ImageView btnAddFoto;
    private CoordinatorLayout coordinatorCadastrarJogador;
    private ImageView imgProfile;
    private TextView btnExcluirFoto;
    private RadioGroup rgPosicao;
    private RadioButton rbGoleiro, rbLinha;

    private int REQUEST_CAMERA = 0;
    private int SELECT_FILE = 1;
    private String FILENAME, picturePath = "";
    private File caminhoDiretorio;
    private int codigoRequisicao;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_jogador);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setIcon(R.mipmap.logo);
        } catch (Exception e) {
            Log.e("Expeption", String.valueOf(e));
        }

        coordinatorCadastrarJogador = (CoordinatorLayout) findViewById(R.id.coordinatorCadastrarJogador);
        txtNomeJogador = (TextInputLayout) findViewById(R.id.txtNomeJogador);
        edtNomeJogador = (EditText) findViewById(R.id.edtNomeJogador);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        imgProfile = (ImageView) findViewById(R.id.imgImagemPerfil);
        btnAddFoto = (ImageView) findViewById(R.id.btnAdicionarFoto);
        rgPosicao = (RadioGroup) findViewById(R.id.radioPosicoes);
        rbGoleiro = (RadioButton) findViewById(R.id.radioGoleiro);
        rbLinha = (RadioButton) findViewById(R.id.radioLinha);
        btnExcluirFoto = (TextView) findViewById(R.id.btnExcluirFoto);

        btnExcluirFoto.setVisibility(View.INVISIBLE);
        rbLinha.setChecked(true);

        Date dt = new Date();
        FILENAME = "_";
        FILENAME += String.valueOf(dt.getSeconds());
        FILENAME += String.valueOf(dt.getHours());
        FILENAME += String.valueOf(dt.getMinutes());
        FILENAME += String.valueOf(dt.getMonth());
        FILENAME += String.valueOf(dt.getYear());
        FILENAME += String.valueOf(dt.getDay());

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSalvar();
            }
        });

        btnAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnExcluirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgProfile.setImageResource(R.drawable.profile1);
                btnAddFoto.setImageResource(R.mipmap.mais);
                btnExcluirFoto.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void submitSalvar() {
        if (validarNome() || validarPosicao()) {
            String nomeJogador = edtNomeJogador.getText().toString();
            int posicao;

            switch (rgPosicao.getCheckedRadioButtonId()) {
                case R.id.radioGoleiro:
                    posicao = 1;
                    break;
                case R.id.radioLinha:
                    posicao = 2;
                    break;
            }
            Snackbar snackbar = Snackbar.make(coordinatorCadastrarJogador, nomeJogador + " cadastrado!", Snackbar.LENGTH_SHORT);
            snackbar.show();
            edtNomeJogador.setText("");
            edtNomeJogador.requestFocus();
        }

        if (codigoRequisicao == 1){
            try {
                OutputStream outFile = new FileOutputStream(caminhoDiretorio + File.separator + edtNomeJogador.getText().toString() + FILENAME + ".jpg");
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                outFile.flush();
                outFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(codigoRequisicao == 2)
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                String sourceImagePath = picturePath;
                String destinationImagePath = caminhoDiretorio + File.separator + edtNomeJogador.getText().toString() + FILENAME + ".jpg";
                File source = new File(sourceImagePath);
                File destination = new File(destinationImagePath);
                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }else{
                Toast.makeText(ActivityCadastrarJogador.this, "Não foi possível obter a imagem.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("Error:", e.toString());
        }else{

        }
    }

    private boolean validarPosicao(){
        if(rbGoleiro.isChecked() || rbLinha.isChecked())
            return true;

        return false;
    }

    private boolean validarNome() {
        if (edtNomeJogador.getText().toString().trim().isEmpty()) {
            txtNomeJogador.setError(getString(R.string.nome_jogador_error));
            edtNomeJogador.requestFocus();
            return false;
        } else {
            txtNomeJogador.setErrorEnabled(false);
        }
        return true;
    }

    private void selectImage() {

        final CharSequence[] options = { "Tirar Foto", "Escolher na Galeria", "Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCadastrarJogador.this);
        builder.setTitle("Adicionar Foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                caminhoDiretorio = new File(android.os.Environment.getExternalStorageDirectory(), "pernetas");
                if (options[item].equals("Tirar Foto"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(!caminhoDiretorio.exists())
                        caminhoDiretorio.mkdirs();

                        File filesave =  new File(caminhoDiretorio, FILENAME + ".jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filesave));
                        startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Escolher na Galeria"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        codigoRequisicao = requestCode;
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString() + "/pernetas");
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals(FILENAME + ".jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    imgProfile.setImageBitmap(bitmap);
                    btnAddFoto.setImageResource(R.mipmap.edit);
                    btnExcluirFoto.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                btnAddFoto.setImageResource(R.mipmap.edit);
                imgProfile.setImageBitmap(thumbnail);
                btnExcluirFoto.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_cadastrar_jogador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.fechar_janela:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.txtNomeJogador:
                    validarNome();
                    break;
            }
        }
    }
}
