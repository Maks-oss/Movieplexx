import * as React from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { Stack } from '@mui/material';
import { useMovieplexxContext } from '../../utils/MovieplexxContext';
import useApiService from "../../utils/ApiService";
const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));
const ReportTable = ({ data }) => {
    return (

        <TableContainer>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <StyledTableCell align="left">Movie ID</StyledTableCell>
                        <StyledTableCell align="center">Movie name</StyledTableCell>
                        <StyledTableCell align="right">Total money spent</StyledTableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data.map((manager, index) => (
                        <TableRow key={index} sx={{ '&:last-child td, &:last-child th': { border: 0 }}}>
                            <TableCell component="th" scope="row" align="left">
                                {manager.movieId}
                            </TableCell>
                            <TableCell align="center">{manager.movieName}</TableCell>
                            <TableCell align="right">{manager.totalPrice.toFixed(2)}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}
const ManagerPicker = ({ names, manager, onChange }) => {
    return (
        <FormControl variant="standard" sx={{ m: 1, width: '50%' }}>
            <InputLabel id="demo-simple-select-standard-label">Manager Name</InputLabel>
            <Select
                labelId="demo-simple-select-standard-label"
                id="demo-simple-select-standard"
                value={manager}
                onChange={onChange}
                label="fullName"
            >
                {names.map((managerName) => (
                    <MenuItem value={managerName}>{managerName}</MenuItem>
                ))
                }
            </Select>
        </FormControl>
    );
}
const SecondReportPage = () => {
    const apiService = useApiService()
    const [reportData, setReportData] = React.useState(null);
    const [currentManager, setCurrentManager] = React.useState('');
    const { endpoints } = useMovieplexxContext();
    React.useEffect(() => {
        apiService.fetchApi(`http://localhost:5433/reports/nedim`)
            .then((data) => {
                setReportData(data);
                setCurrentManager(Object.keys(data)[0])
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }, [endpoints]);
    const handleChange = (event) => {
        setCurrentManager(event.target.value);
    };
    if (reportData) {
        return (
            <Box
                display="flex"
                justifyContent="center"
                alignItems="center"
                minHeight="100vh"
            >
                <Stack spacing={2}>
                    <ManagerPicker names={Object.keys(reportData)} manager={currentManager} onChange={handleChange} />
                    <ReportTable data={reportData[currentManager]} />
                </Stack>
            </Box>
        );
    }

};

export default SecondReportPage;
