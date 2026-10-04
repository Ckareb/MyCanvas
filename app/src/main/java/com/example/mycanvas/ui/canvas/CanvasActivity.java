package com.example.mycanvas.ui.canvas;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mycanvas.R;
import com.example.mycanvas.model.ref.Colors;
import com.example.mycanvas.ui.base.BaseActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CanvasActivity extends BaseActivity {

    private Spinner spinnerColorCanvas;

    private Spinner spinnerBrushCanvas;

    private List<Colors> colors = Arrays.asList(Colors.values());

    private CanvasView canvasView;

    @Override
    protected int getLayoutId() {
        return R.layout.canvas;
    }

    @Override
    protected int getRootViewId() {
        return R.id.canvasRoot;
    }

    @Override
    protected void onViewReady() {
        createMenu();
    }

    private void createMenu() {
        setSpinner();

        changeColor();

        createButton();

        clearButton();
    }

    private void setSpinner() {
        List<String> colorCanvasList = new ArrayList<>();

        List<String> brushCanvasList = new ArrayList<>();

        colors.forEach(color -> {
            colorCanvasList.add(color.getTitle());
            brushCanvasList.add(color.getTitle());
        });

        spinnerColorCanvas = findViewById(R.id.colorCanvas);

        ArrayAdapter<String> adapterColorCanvas = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                colorCanvasList
        );

        adapterColorCanvas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerColorCanvas.setAdapter(adapterColorCanvas);

        spinnerBrushCanvas = findViewById(R.id.brushCanvas);

        ArrayAdapter<String> adapterBrushCanvas= new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                brushCanvasList
        );

        adapterBrushCanvas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBrushCanvas.setAdapter(adapterBrushCanvas);
    }

    private void changeColor() {

        canvasView = findViewById(R.id.canvasView);

        spinnerColorCanvas.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Colors color = colors.get(position);

                        canvasView.setColorCanvas(color.getColor());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent){
                    }
                }
        );

        spinnerBrushCanvas.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Colors color = colors.get(position);

                        canvasView.setColorBrush(color.getColor());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent){
                    }
                }
        );
    }

    private void createButton() {
        canvasView = findViewById(R.id.canvasView);

        Button button = findViewById(R.id.buttonSave);

        button.setOnClickListener(v -> {
            Bitmap bitmap = canvasView.getBitmap();

            saveImage(bitmap);
        });
    }

    private void saveImage(Bitmap bitmap) {

        ContentValues values = new ContentValues();

        values.put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "drawing_" + System.currentTimeMillis() + ".png"
        );

        values.put(
                MediaStore.Images.Media.MIME_TYPE,
                "image/png"
        );

        values.put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES
        );

        Uri uri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
        );

        if (uri != null) {

            try (OutputStream outputStream =
                         getContentResolver().openOutputStream(uri)) {

                bitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        outputStream
                );

                Toast.makeText(
                        this,
                        "Изображение сохранено",
                        Toast.LENGTH_SHORT
                ).show();

            } catch (IOException e) {

                Toast.makeText(
                        this,
                        "Ошибка сохранения",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private void clearButton() {
        canvasView = findViewById(R.id.canvasView);

        Button button = findViewById(R.id.buttonClear);

        button.setOnClickListener(v -> {
            canvasView.clear();
        });
    }
}
