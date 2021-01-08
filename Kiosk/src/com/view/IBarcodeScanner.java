package com.view;

public interface IBarcodeScanner {
    void register(IBarcodeScannerObserver observer);
    void remove(IBarcodeScannerObserver observer);
    void scanBarcode(String barcode);
}
