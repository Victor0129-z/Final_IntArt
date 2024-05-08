package Numeros;

import java.io.*;

public class MnistDataReader  {

    public MnistMatrix[] readData(String dataFilePath, String labelFilePath) throws IOException {

        MnistMatrix[] data;
        int magicNumber, numberOfItems, nRows, nCols, labelMagicNumber, numberOfLabels;
        DataInputStream labelInputStream;
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFilePath)))) {
            magicNumber = dataInputStream.readInt();
            numberOfItems = dataInputStream.readInt();
            nRows = dataInputStream.readInt();
            nCols = dataInputStream.readInt();
            // Arreglo de imágenes y etiquetas
            data = new MnistMatrix[numberOfItems];
            
            labelInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(labelFilePath)));
            labelMagicNumber = labelInputStream.readInt();
            numberOfLabels = labelInputStream.readInt();
            // Se asegura que la cantidad de imágenes se idéntica a la cantidad de etiquetas
            assert numberOfItems == numberOfLabels;
            for (int i = 0; i < numberOfItems; i++) {
                MnistMatrix mnistMatrix = new MnistMatrix(nRows, nCols);
                mnistMatrix.setLabel(labelInputStream.readUnsignedByte());
                // Establece el valor de cada pixel en la matriz de pixeles
                for (int r = 0; r < nRows; r++) {
                    for (int c = 0; c < nCols; c++) {
                        mnistMatrix.setValue(r, c, dataInputStream.readUnsignedByte());
                    }
                }
                data[i] = mnistMatrix;
            }
            // Cierra la lectura de archivos
            dataInputStream.close();
            labelInputStream.close();
        }
        
        // Identificador que determina el tipo de archivo;  2051: archivo de imágenes, 2049: archivo de etiquetas
        System.out.println("Número mágico del archivo de imágenes" + magicNumber); // 2051
        System.out.println("Numero de elementos " + numberOfItems);
        System.out.println("Número de filas: " + nRows);
        System.out.println("Número de columnas: " + nCols);
        System.out.println("Número mágico del archivo de etiquetas: " + labelMagicNumber); // 2049
        System.out.println("Número de etiquetas: " + numberOfLabels);
        
        return data;
    }
}
