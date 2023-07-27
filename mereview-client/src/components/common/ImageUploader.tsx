import { useCallback, useState } from "react";
import { useDropzone } from "react-dropzone";
import "../../styles/css/ProfileImageUpload.css";
function ImageUploader() {
  const [selectedImage, setSelectedImage] = useState<string | null>(null);

  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];

    if (file) {
      const objectURL = URL.createObjectURL(file);
      setSelectedImage(objectURL);
    }
  }, []);

  const { getRootProps, getInputProps } = useDropzone({
    onDrop,
    accept: {
      "image/*": [".jpeg", ".jpg", ".png", ".gif"],
    },
    maxFiles: 1,
  });

  return (
    <div className="fileUpload d-flex align-items-center">
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
          src={"/defaultProfile.png"}
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
