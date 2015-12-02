package com.asymmetrik.utils.hash;

public class TestAverageHash extends TestHashGen {

    @Override
    protected HashGen createInstance() {
        return new AverageHash();
    }
}
