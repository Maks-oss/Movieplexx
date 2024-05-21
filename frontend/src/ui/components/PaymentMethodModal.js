import React, {useState} from 'react';
import {
    Container,
    Radio,
    FormControl,
    FormControlLabel,
    FormLabel,
    RadioGroup,
    Button,
    Box,
    Typography
} from '@mui/material';
import Modal from '@mui/material/Modal';
import Fade from '@mui/material/Fade';
import Backdrop from '@mui/material/Backdrop';
import LinearProgress from '@mui/material/LinearProgress';

const paymentMethods = [
    {value: 'creditCard', label: 'Credit Card'},
    {value: 'paypal', label: 'PayPal'},
    {value: 'bank', label: 'Bank Transfer'}
];

const ChoosePaymentMethod = ({confirm,onConfirmClick}) => {
    const [selectedMethod, setSelectedMethod] = useState('');
    // const [confirm, setConfirm] = useState(false)
    const handleChange = (event) => {
        setSelectedMethod(event.target.value);
    };

    // const handleSubmit = () => {
    //     setConfirm(true)
    // };
    if (!confirm) {
        return (
            <Container maxWidth="sm">
                <FormControl component="fieldset">
                    <FormLabel component="legend">Choose Payment Method</FormLabel>
                    <RadioGroup value={selectedMethod} onChange={handleChange}>
                        {paymentMethods.map((method) => (
                            <FormControlLabel
                                key={method.value}
                                value={method.value}
                                control={<Radio/>}
                                label={
                                    <Box display="flex" alignItems="center">
                                        {method.icon}
                                        <Box ml={1}>{method.label}</Box>
                                    </Box>
                                }
                            />
                        ))}
                    </RadioGroup>
                </FormControl>
                <Box mt={2}>
                    <Button variant="contained" color="primary" onClick={onConfirmClick} disabled={!selectedMethod}>
                        Confirm Payment
                    </Button>
                </Box>
            </Container>
        );
    } else {
        return (
            <div>
                <Typography id="transition-modal-title" variant="h6" component="h2" sx={{marginBottom: '10px'}}>
                    Processing payment...
                </Typography>
                <LinearProgress/>
            </div>
        )
    }
};
const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 4,
};
const PaymentMethodModal = ({open, handleClose,confirm,onConfirmClick}) => {
    return (
        <Modal
            aria-labelledby="transition-modal-title"
            aria-describedby="transition-modal-description"
            open={open}
            onClose={handleClose}
            closeAfterTransition
            slots={{backdrop: Backdrop}}
            slotProps={{
                backdrop: {
                    timeout: 500,
                },
            }}
        >
            <Fade in={open}>
                <Box sx={modalStyle}>
                    <ChoosePaymentMethod confirm={confirm} onConfirmClick={onConfirmClick}/>
                </Box>
            </Fade>
        </Modal>)
}

export default PaymentMethodModal
