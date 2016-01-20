package com.tas.speech.sections.game.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drago_000 on 01/07/2015.
 */
public class PromptProperties {

    //Prompt properties
    private String UserId;
    private String PictureUrl;
    private String Name;
    private String Delay;
    private String Text;
    private String HeaderText;
    private String AudioUrl;
    private String PersonalMessage;

    // RatingInputPrompt properties
    private String LinkedNodeId;
    private String LinkedEntityId;
    private String Username;
    private List<Metric> Metrics;

    // RecordInput properties
    private String MinLength;
    private String MaxLength;
    private String MaxRecordings;
    private String MaxRetriesPerRecording;

    // MultipleChoiceInput properties
    private String SuccessMessage, InsuccessMessage;
    private ArrayList<PromptMultipleChoiceOption> Options;

    public PromptProperties() {

    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPictureUrl() {
        return PictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        PictureUrl = pictureUrl;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHeaderText() {
        return HeaderText;
    }

    public void setHeaderText(String headerText) {
        HeaderText = headerText;
    }

    public String getAudioUrl() {
        return AudioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        AudioUrl = audioUrl;
    }

    public String getPersonalMessage() {
        return PersonalMessage;
    }

    public void setPersonalMessage(String personalMessage) {
        PersonalMessage = personalMessage;
    }

    public String getLinkedEntityId() {
        return LinkedEntityId;
    }

    public void setLinkedEntityId(String linkedEntityId) {
        LinkedEntityId = linkedEntityId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public List<Metric> getMetrics() {
        return Metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        Metrics = metrics;
    }

    public String getMinLength() {
        return MinLength;
    }

    public void setMinLength(String minLength) {
        MinLength = minLength;
    }

    public String getMaxLength() {
        return MaxLength;
    }

    public void setMaxLength(String maxLength) {
        MaxLength = maxLength;
    }

    public String getMaxRecordings() {
        return MaxRecordings;
    }

    public void setMaxRecordings(String maxRecordings) {
        MaxRecordings = maxRecordings;
    }

    public String getMaxRetriesPerRecording() {
        return MaxRetriesPerRecording;
    }

    public void setMaxRetriesPerRecording(String maxRetriesPerRecording) {
        MaxRetriesPerRecording = maxRetriesPerRecording;
    }

    public String getSuccessMessage() {
        return SuccessMessage;
    }

    public void setSuccessMessage(String successMessage) {
        SuccessMessage = successMessage;
    }

    public String getInsuccessMessage() {
        return InsuccessMessage;
    }

    public void setInsuccessMessage(String insuccessMessage) {
        InsuccessMessage = insuccessMessage;
    }

    public ArrayList<PromptMultipleChoiceOption> getOptions() {
        return Options;
    }

    public void setOptions(ArrayList<PromptMultipleChoiceOption> options) {
        Options = options;
    }

    public String getDelay() {
        return Delay;
    }

    public void setDelay(String delay) {
        Delay = delay;
    }

    public String getLinkedNodeId() {
        return LinkedNodeId;
    }

    public void setLinkedNodeId(String linkedNodeId) {
        LinkedNodeId = linkedNodeId;
    }

    public String toString() {
        return "[UserId: " + UserId
        + ", PictureUrl: " + PictureUrl
        + ", Name: " + Name
        + ", Text: " + Text
        + ", HeaderText: " + HeaderText
        + ", AudioUrl: " + AudioUrl
        + ", PersonalMessage: " + PersonalMessage
                + ", MaxLength: " + MaxLength
                + ", MinLength: " + MinLength + "]";
    }
}
