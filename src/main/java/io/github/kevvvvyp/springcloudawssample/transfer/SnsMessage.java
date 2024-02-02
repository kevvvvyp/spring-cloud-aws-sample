package io.github.kevvvvyp.springcloudawssample.transfer;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SnsMessage implements Serializable {
	@JsonProperty("Type")
	private String type;
	@JsonProperty("MessageId")
	private String id;
	@JsonProperty("Message")
	private String message;

	//etc...
}
