package com.asymmetrik.utils.hash;

public class TestAverageHash extends TestIHashGen {

    @Override
    protected IHashGen createInstance() {
        return new AverageHash();
    }
}
