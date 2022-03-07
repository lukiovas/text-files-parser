import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  myFiles:string [] = [];

  myForm = new FormGroup({
    file: new FormControl('', [Validators.required])
  });

  constructor(private http: HttpClient) { }

  get f(){
    return this.myForm.controls;
  }

  onFileChange(event:any) {

    for (var i = 0; i < event.target.files.length; i++) {
      this.myFiles.push(event.target.files[i]);
    }
  }

  submit(){
    const formData = new FormData();

    for (var i = 0; i < this.myFiles.length; i++) {
      formData.append("textFiles", this.myFiles[i]);
    }

    this.http.post('http://localhost:8080/upload', formData, {responseType: 'text'})
      .subscribe(res => {
        console.log(res);
        alert('Uploaded Successfully.');
      },
      error => {
        console.log(error);
      })
  }
}
