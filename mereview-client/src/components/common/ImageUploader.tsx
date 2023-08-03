import { useCallback, useState } from "react";
import { useDropzone } from "react-dropzone";
import "../../styles/css/ImageUploader.css";
import { userActions } from "../../store/user-slice";
import { useDispatch } from "react-redux";
function ImageUploader() {
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const dispatch = useDispatch();
  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];

    if (file) {
      const objectURL = URL.createObjectURL(file);
      setSelectedImage(objectURL);
      dispatch(userActions.signUp_step2(file));
    }
    console.log(file);
  }, []);

  const { getRootProps, getInputProps } = useDropzone({
    onDrop,
    accept: {
      "image/*": [".jpeg", ".jpg", ".png", ".gif"],
    },
    maxFiles: 1,
  });

  return (
    <div className="fileUpload ">
      {selectedImage ? (
        <img
          src={selectedImage}
          alt="Preview"
          style={{
            width: "12rem",
            height: "12rem",
            borderRadius: "9999px",
            border: "1px solid black",
          }}
        />
      ) : (
        <img
          src={"/testProfile.gif"}
          alt="defaulImg"
          style={{
            width: "12rem",
            height: "12rem",
            borderRadius: "9999px",
            border: "1px solid black",
            backgroundColor: "gray",
          }}
        />
      )}
      <div className="inputBox" {...getRootProps()}>
        <input {...getInputProps()} />
        <p>여기에 이미지를 끌어다 놓거나 클릭하여 이미지를 선택하세요.</p>
      </div>
    </div>
  );
}

export default ImageUploader;
