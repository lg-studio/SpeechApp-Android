package com.tas.speech.sections.game.models;

import com.tas.speech.sections.game.prompts.PromptTypes;

/**
 * Created by drago_000 on 01/07/2015.
 */
public class Prompt {

    public enum Category {OUTPUT, INPUT, FEEDBACK_REQUIRED}

    private String Id;
    private String NodeId;
    private String Type;
    private PromptProperties Properties;

    public Prompt() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public PromptProperties getProperties() {
        return Properties;
    }

    public void setProperties(PromptProperties properties) {
        this.Properties = properties;
    }

    public String getNodeId() {
        return NodeId;
    }

    public void setNodeId(String nodeId) {
        NodeId = nodeId;
    }

    public Category getPromptCategory() {
        if (Type.equals(PromptTypes.TEXT) || Type.equals(PromptTypes.AUDIO)) {
            return Category.OUTPUT;
        }
        else if(Type.equals(PromptTypes.RECORDING) || Type.equals(PromptTypes.RATING)
                || Type.equals(PromptTypes.SUBMIT) || Type.equals(PromptTypes.MULTIPLE_CHOICE_TEXT)
                || Type.equals(PromptTypes.MULTIPLE_CHOICE_PICTURE)) {
            return Category.INPUT;
        }
        return Category.FEEDBACK_REQUIRED;
    }

    public String toString() {
        return "[Id: " + Id
                + ", Type: " + Type
                + ", Properties: " + Properties + "]";
    }
}
