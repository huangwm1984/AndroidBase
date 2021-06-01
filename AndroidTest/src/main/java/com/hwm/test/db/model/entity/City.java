package com.hwm.test.db.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 市信息数据表
 */
@DatabaseTable(tableName = "city")
public class City {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(unique = true)
    private String cityNo;

    @DatabaseField
    private String cityName;

    @DatabaseField(defaultValue = "-1")
    private Integer index;

    @DatabaseField
    private String test;

    @DatabaseField
    private String provinceName;

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityNo='" + cityNo + '\'' +
                ", cityName='" + cityName + '\'' +
                ", index=" + index +
                ", test='" + test + '\'' +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
