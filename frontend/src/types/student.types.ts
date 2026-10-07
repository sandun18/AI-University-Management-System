export type StudentStatus =
  | 'ACTIVE'
  | 'INACTIVE'
  | 'SUSPENDED'
  | 'GRADUATED'
  | 'DROPPED';

export interface Student {
  id: number;
  studentId: string;
  userId?: number | null;
  firstName: string;
  lastName: string;
  email: string;
  phone?: string | null;
  dateOfBirth?: string | null;
  gender?: string | null;
  address?: string | null;
  department: string;
  degreeProgram: string;
  yearOfStudy: number;
  semester?: number | null;
  status: StudentStatus;
  createdAt?: string;
  updatedAt?: string;
}

export interface StudentRequest {
  studentId: string;
  userId?: number | null;
  firstName: string;
  lastName: string;
  email: string;
  phone?: string;
  dateOfBirth?: string;
  gender?: string;
  address?: string;
  department: string;
  degreeProgram: string;
  yearOfStudy: number;
  semester?: number;
  status?: StudentStatus;
}

export interface StudentFilters {
  query?: string;
  department?: string;
  status?: StudentStatus | 'ALL';
  degreeProgram?: string;
  yearOfStudy?: number;
}

export interface StudentDeleteResponse {
  success: boolean;
  message: string;
  id: number;
}
