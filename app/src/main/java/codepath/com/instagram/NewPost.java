package codepath.com.instagram;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseFile;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

public class NewPost extends Fragment {

    CameraView camera;
    Button btTake;

    public NewPost() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        camera = (CameraView) view.findViewById(R.id.camera);
        btTake = (Button) view.findViewById(R.id.btTake);


        camera.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                ParseFile photoFile = new ParseFile(cameraKitImage.getJpeg());

                // Create a bitmap
                Bitmap result = cameraKitImage.getBitmap();
                Toast.makeText(getActivity(), "Noice.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        btTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.captureImage();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        camera.start();
    }

    @Override
    public void onPause() {
        camera.stop();
        super.onPause();
    }
}
