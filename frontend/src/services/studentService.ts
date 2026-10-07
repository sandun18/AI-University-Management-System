import api from '@/api/axios';
import {
  Student,
  StudentRequest,
  StudentStatus,
  StudentDeleteResponse,
} from '@/types/student.types';

export const studentService = {
  /**
   * Fetch all registered students
   */
  async getAllStudents(): Promise<Student[]> {
    const response = await api.get<Student[]>('/api/students');
    return response.data;
  },

  /**
   * Fetch a single student by primary key ID
   */
  async getStudentById(id: number): Promise<Student> {
    const response = await api.get<Student>(`/api/students/${id}`);
    return response.data;
  },

  /**
   * Fetch a student by registration student ID (e.g. STU2025001)
   */
  async getStudentByStudentId(studentId: string): Promise<Student> {
    const response = await api.get<Student>(`/api/students/registration/${encodeURIComponent(studentId)}`);
    return response.data;
  },

  /**
   * Fetch a student profile linked to auth user ID
   */
  async getStudentByUserId(userId: number): Promise<Student> {
    const response = await api.get<Student>(`/api/students/user/${userId}`);
    return response.data;
  },

  /**
   * Search students across name, student ID, email, and department
   */
  async searchStudents(query: string): Promise<Student[]> {
    const response = await api.get<Student[]>('/api/students/search', {
      params: { query },
    });
    return response.data;
  },

  /**
   * Filter students by department
   */
  async getStudentsByDepartment(department: string): Promise<Student[]> {
    const response = await api.get<Student[]>(`/api/students/department/${encodeURIComponent(department)}`);
    return response.data;
  },

  /**
   * Filter students by department and year of study
   */
  async getStudentsByDepartmentAndYear(department: string, yearOfStudy: number): Promise<Student[]> {
    const response = await api.get<Student[]>(
      `/api/students/department/${encodeURIComponent(department)}/year/${yearOfStudy}`
    );
    return response.data;
  },

  /**
   * Filter students by degree program
   */
  async getStudentsByProgram(degreeProgram: string): Promise<Student[]> {
    const response = await api.get<Student[]>(`/api/students/program/${encodeURIComponent(degreeProgram)}`);
    return response.data;
  },

  /**
   * Filter students by status
   */
  async getStudentsByStatus(status: StudentStatus): Promise<Student[]> {
    const response = await api.get<Student[]>(`/api/students/status/${status}`);
    return response.data;
  },

  /**
   * Create and register a new student
   */
  async createStudent(payload: StudentRequest): Promise<Student> {
    const response = await api.post<Student>('/api/students', payload);
    return response.data;
  },

  /**
   * Update all details of an existing student
   */
  async updateStudent(id: number, payload: StudentRequest): Promise<Student> {
    const response = await api.put<Student>(`/api/students/${id}`, payload);
    return response.data;
  },

  /**
   * Update student status (e.g. ACTIVE -> SUSPENDED, GRADUATED)
   */
  async updateStudentStatus(id: number, status: StudentStatus): Promise<Student> {
    const response = await api.patch<Student>(`/api/students/${id}/status`, null, {
      params: { status },
    });
    return response.data;
  },

  /**
   * Delete a student by ID
   */
  async deleteStudent(id: number): Promise<StudentDeleteResponse> {
    const response = await api.delete<StudentDeleteResponse>(`/api/students/${id}`);
    return response.data;
  },
};

export default studentService;
