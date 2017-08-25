package com.yg.reservation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="files")
@Getter
@Setter
@ToString
public class Image {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@Column(name="file_name")
	private String fileName;
	@Column(name="save_file_name")
	private String saveFileName;
	@Column(name="file_length")
	private long fileLength;
	@Column(name="content_type")
	private String contentType;
	@Column(name="delete_flag")
	private int deleteFlag;
}
