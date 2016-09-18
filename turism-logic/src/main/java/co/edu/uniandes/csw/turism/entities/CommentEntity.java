/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.turism.entities;

import co.edu.uniandes.csw.crud.api.podam.strategy.DateStrategy;
import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author da.prieto1
 */
@Entity
public class CommentEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @ManyToOne
    private ClientEntity client;

    @PodamExclude
    @ManyToOne
    private TripEntity trip;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date date;

    private String text;

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public TripEntity getTrip() {
        return trip;
    }

    public void setTrip(TripEntity trip) {
        this.trip = trip;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}