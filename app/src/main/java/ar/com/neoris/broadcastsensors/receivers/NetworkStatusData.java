package ar.com.neoris.broadcastsensors.receivers;

import java.io.Serializable;

/**
 * Created by juani on 17/10/16.
 */

public class NetworkStatusData implements Serializable{

    public static final String NETWORK_STATUS_ACTION = "ar.com.neoris.NETWORK_STATUS_ACTION";

    public enum Tipo {
        _3G("3G"), _4G("4G"), GPRS("GPRS");

        private String desc;

        Tipo(String desc){
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    private int range;
    private Tipo tipo;

    private NetworkStatusData(NetworkStatusDataBuilder builder){
        this.range = builder.range;
        this.tipo = builder.tipo;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public static class NetworkStatusDataBuilder{
        private int range;
        private Tipo tipo;

        public NetworkStatusDataBuilder range(int range){
            this.range = range;
            return this;
        }

        public NetworkStatusDataBuilder tipo(Tipo tipo){
            this.tipo = tipo;
            return this;
        }

        public NetworkStatusData build(){
            return new NetworkStatusData(this);
        }
    }

}
