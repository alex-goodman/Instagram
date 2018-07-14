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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.parse.ParseFile;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

public class CameraFragment extends Fragment {

    CameraView camera;
    Button btTake;
    onPicTakenListener listener;
    ImageView ivFlip;
    int originCode;

    public CameraFragment() {
        // Required empty public constructor
    }

    public interface onPicTakenListener {
        public void onPicTaken(Bitmap bmp, ParseFile file);
        public void onProfPicTaken(Bitmap bmp, ParseFile file);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    public void setup(int code) {
        originCode = code;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        camera = (CameraView) view.findViewById(R.id.camera);
        btTake = (Button) view.findViewById(R.id.btTake);
        listener = (onPicTakenListener) getActivity();
        ivFlip = (ImageView) view.findViewById(R.id.ivFlip);
        final RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.relLay);

        // make the camera square
        layout.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = camera.getLayoutParams();
                params.height = camera.getWidth();
                camera.setLayoutParams(params);
                layout.postInvalidate();
            }
        });

        if (originCode == HomeActivity.FROM_PROF_CODE) camera.setFacing(CameraKit.Constants.FACING_FRONT);

        ivFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera.getFacing() == CameraKit.Constants.FACING_BACK) camera.setFacing(CameraKit.Constants.FACING_FRONT);
                else camera.setFacing(CameraKit.Constants.FACING_BACK);
            }
        });

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

                // Create a bitmap, pass origin code into listener
                Bitmap result = cameraKitImage.getBitmap();
                if (originCode == HomeActivity.FROM_PROF_CODE) {
                    listener.onProfPicTaken(result, photoFile);
                } else {
                    listener.onPicTaken(result, photoFile);
                }
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
