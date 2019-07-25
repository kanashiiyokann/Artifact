package artifact.modules.assets.entity;

import java.math.BigDecimal;

/**
 * 固定资产折旧（记录）
 *
 * @author DGG-S27-D-20
 */
public class Depreciation {
    /**
     * 折旧账期
     */
    private Integer period;
    /**
     * 折旧方法
     */
    private String type;
    /**
     * 折旧额
     */
    private BigDecimal account;
    /**
     * 月折旧率
     */
    private Double rate;

    /**
     * 净值
     */
    private BigDecimal net;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }
}
