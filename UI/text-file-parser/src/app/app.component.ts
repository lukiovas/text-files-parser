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
  myMap = new Map<String,Map<string, number>>();
  myForm = new FormGroup({
    file: new FormControl('', [Validators.required])
  });

  words:Object = '';

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

    this.http.post('http://localhost:8080/upload', formData, {responseType: 'json'})
      .subscribe(res => {
        const stringResponse = JSON.stringify(res);
        const objectResponse = JSON.parse(stringResponse);
        this.myMap = new Map(Object.entries(objectResponse));
        console.log(this.myMap);
        alert('Uploaded Successfully.');
      },
      error => {
        console.log(error);
      })
      this.myFiles = [];
      this.myForm.reset();
  }
}
