package Model;

public class WirelessNet {

    String SSID;
    String ifName;
    String ipAddress;
    String macAddress;
    String gwAddress;
    String dns1Address;
    String dns2Address;

    boolean conected;

    int level;
    int sum_level;
    int cont_level;
    int avg_level;

    int service;
    int sum_service;
    int cont_service;
    int avg_service;

    int score;
    int sum_score;
    int cont_score;
    int avg_score;

    int channel;
    boolean key;
    int frequency;

    float overlap_factor;
    float sum_olf;
    float cont_olf;
    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }
    public String getIfName() {
        return ifName;
    }

    public void setIfName(String ifName) {
        this.ifName = ifName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getGwAddress() {
        return gwAddress;
    }

    public void setGwAddress(String gwAddress) {
        this.gwAddress = gwAddress;
    }

    public String getDns1Address() {
        return dns1Address;
    }

    public void setDns1Address(String dns1Address) {
        this.dns1Address = dns1Address;
    }

    public String getDns2Address() {
        return dns2Address;
    }

    public void setDns2Address(String dns2Address) {
        this.dns2Address = dns2Address;
    }

    public boolean isConected() {
        return conected;
    }

    public void setConected(boolean conected) {
        this.conected = conected;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSum_level() {
        return sum_level;
    }

    public void setSum_level(int sum_level) {
        this.sum_level = sum_level;
    }

    public int getCont_level() {
        return cont_level;
    }

    public void setCont_level(int cont_level) {
        this.cont_level = cont_level;
    }

    public int getAvg_level() {
        return avg_level;
    }

    public void setAvg_level(int avg_level) {
        this.avg_level = avg_level;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getSum_service() {
        return sum_service;
    }

    public void setSum_service(int sum_service) {
        this.sum_service = sum_service;
    }

    public int getCont_service() {
        return cont_service;
    }

    public void setCont_service(int cont_service) {
        this.cont_service = cont_service;
    }

    public int getAvg_service() {
        return avg_service;
    }

    public void setAvg_service(int avg_service) {
        this.avg_service = avg_service;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSum_score() {
        return sum_score;
    }

    public void setSum_score(int sum_score) {
        this.sum_score = sum_score;
    }

    public int getCont_score() {
        return cont_score;
    }

    public void setCont_score(int cont_score) {
        this.cont_score = cont_score;
    }

    public int getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(int avg_score) {
        this.avg_score = avg_score;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public float getOverlap_factor() {
        return overlap_factor;
    }

    public void setOverlap_factor(float overlap_factor) {
        this.overlap_factor = overlap_factor;
    }

    public float getSum_olf() {
        return sum_olf;
    }

    public void setSum_olf(float sum_olf) {
        this.sum_olf = sum_olf;
    }

    public float getCont_olf() {
        return cont_olf;
    }

    public void setCont_olf(float cont_olf) {
        this.cont_olf = cont_olf;
    }

    public float getAvg_olf() {
        return avg_olf;
    }

    public void setAvg_olf(float avg_olf) {
        this.avg_olf = avg_olf;
    }

    public float getScore_snr() {
        return score_snr;
    }

    public void setScore_snr(float score_snr) {
        this.score_snr = score_snr;
    }

    float avg_olf;
    float score_snr;

}
